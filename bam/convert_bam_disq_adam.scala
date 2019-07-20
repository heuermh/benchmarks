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
import htsjdk.samtools.{ SAMFileHeader, SAMRecord }
import org.bdgenomics.adam.converters.SAMRecordConverter
import org.bdgenomics.adam.rdd.ADAMContext._
import org.bdgenomics.adam.rdd.read.AlignmentRecordDataset
import org.bdgenomics.formats.avro.AlignmentRecord
import org.disq_bio.disq.HtsjdkReadsRdd
import org.disq_bio.disq.HtsjdkReadsRddStorage
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger("disqConvert")
val inputPath = Option(System.getenv("INPUT"))
val outputPath = Option(System.getenv("OUTPUT"))

if (!inputPath.isDefined || !outputPath.isDefined) {
  logger.error("INPUT and OUTPUT environment variables are required")
  System.exit(1)
}

val htsjdkReadsRddStorage = HtsjdkReadsRddStorage.makeDefault(sc)
val htsjdkReadsRdd = htsjdkReadsRddStorage.read(inputPath.get)

val header = htsjdkReadsRdd.getHeader()
val references = sc.loadBamReferences(header)
val readGroups = sc.loadBamReadGroups(header)
val processingSteps = sc.loadBamProcessingSteps(header)

val reads = htsjdkReadsRdd.getReads()
val converter = new SAMRecordConverter()
val alignmentRdd = reads.rdd.map(converter.convert(_))

val alignments = AlignmentRecordDataset(alignmentRdd, references, readGroups, processingSteps)
alignments.saveAsParquet(outputPath.get)

System.exit(0)
