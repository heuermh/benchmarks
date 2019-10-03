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
import org.bdgenomics.adam.rdd.ADAMContext._
import org.bdgenomics.adam.rdd.read.AlignmentDataset
import org.bdgenomics.convert.ConversionStringency
import org.bdgenomics.convert.htsjdk.{
  SamHeaderToProcessingSteps,
  SamHeaderToReadGroups,
  SamHeaderToReferences,
  SamRecordToAlignment
}
import org.bdgenomics.formats.avro.Alignment
import org.disq_bio.disq.HtsjdkReadsRdd
import org.disq_bio.disq.HtsjdkReadsRddStorage
import org.slf4j.LoggerFactory
import scala.collection.JavaConverters._

val logger = LoggerFactory.getLogger("convert_bam_disq_convert")
val inputPath = Option(System.getenv("INPUT"))
val outputPath = Option(System.getenv("OUTPUT"))

if (!inputPath.isDefined || !outputPath.isDefined) {
  logger.error("INPUT and OUTPUT environment variables are required")
  System.exit(1)
}

val htsjdkReadsRddStorage = HtsjdkReadsRddStorage.makeDefault(sc)
val htsjdkReadsRdd = htsjdkReadsRddStorage.read(inputPath.get)

val header = htsjdkReadsRdd.getHeader()
val references = new SamHeaderToReferences().convert(header, ConversionStringency.STRICT, logger).asScala
val readGroups = new SamHeaderToReadGroups().convert(header, ConversionStringency.STRICT, logger).asScala
val processingSteps = new SamHeaderToProcessingSteps().convert(header, ConversionStringency.STRICT, logger).asScala

val reads = htsjdkReadsRdd.getReads()
val alignmentRdd = reads.rdd.map(new SamRecordToAlignment().convert(_, ConversionStringency.STRICT, logger))

val alignments = AlignmentDataset(alignmentRdd, references, readGroups, processingSteps)
alignments.saveAsParquet(outputPath.get)

System.exit(0)
