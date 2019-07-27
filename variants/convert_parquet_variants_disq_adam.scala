/**
 * Copyright 2019 held jointly by the individual authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import htsjdk.samtools.ValidationStringency
import htsjdk.variant.vcf.VCFHeader
import org.bdgenomics.adam.converters.VariantContextConverter
import org.bdgenomics.adam.rdd.ADAMContext._
import org.disq_bio.disq.HtsjdkVariantsRdd
import org.disq_bio.disq.HtsjdkVariantsRddStorage
import org.disq_bio.disq.FileCardinalityWriteOption
import org.slf4j.LoggerFactory
import scala.collection.JavaConverters._

val logger = LoggerFactory.getLogger("convert_parquet_variants_adam_rdd")
val inputPath = Option(System.getenv("INPUT"))
val outputPath = Option(System.getenv("OUTPUT"))

if (!inputPath.isDefined || !outputPath.isDefined) {
  logger.error("INPUT and OUTPUT environment variables are required")
  System.exit(1)
}

val variants = sc.loadParquetVariants(inputPath.get)

val sampleIds: java.util.Set[String] = Set.empty.asJava
val header = new VCFHeader(variants.headerLines.toSet.asJava, sampleIds)
header.setSequenceDictionary(variants.sequences.toSAMSequenceDictionary)

val converter = VariantContextConverter(variants.headerLines, ValidationStringency.LENIENT, sc.hadoopConfiguration)
val htsjdkVcs = variants.toVariantContexts().rdd.map(vc => converter.convert(vc).getOrElse(null))

val htsjdkVariantsRddStorage = HtsjdkVariantsRddStorage.makeDefault(sc)
val htsjdkVariantsRdd = new HtsjdkVariantsRdd(header, htsjdkVcs.toJavaRDD)
htsjdkVariantsRddStorage.write(htsjdkVariantsRdd, outputPath.get, FileCardinalityWriteOption.SINGLE)

System.exit(0)
