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
import org.bdgenomics.adam.models.ReferenceRegion
import org.bdgenomics.adam.rdd.ADAMContext._
import org.bdgenomics.adam.rdd.ADAMSaveAnyArgs
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger("range_filter_bam_adam_dataset")
val inputPath = Option(System.getenv("INPUT"))

if (!inputPath.isDefined) {
  logger.error("INPUT environment variable is required")
  System.exit(1)
}

val ranges = Seq(ReferenceRegion.fromGenomicRange("1", 100, 200), ReferenceRegion.fromGenomicRange("2", 100, 200))

val alignments = sc.loadIndexedBam(inputPath.get, ranges)
println(alignments.dataset.count())

System.exit(0)

