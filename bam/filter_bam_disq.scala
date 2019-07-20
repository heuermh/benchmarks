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
import htsjdk.samtools.SAMRecord
import org.disq_bio.disq.HtsjdkReadsRdd
import org.disq_bio.disq.HtsjdkReadsRddStorage
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger("filter_bam_disq")
val inputPath = Option(System.getenv("INPUT"))

if (!inputPath.isDefined) {
  logger.error("INPUT environment variable is required")
  System.exit(1)
}

val htsjdkReadsRddStorage = HtsjdkReadsRddStorage.makeDefault(sc)
val htsjdkReadsRdd = htsjdkReadsRddStorage.read(inputPath.get)
val reads = htsjdkReadsRdd.getReads()

def filter(r: SAMRecord): Boolean = {
  true
}

println(reads.rdd.filter(filter(_)).count())

System.exit(0)
