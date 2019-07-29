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
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger("rw_cram_adam")
val inputPath = Option(System.getenv("INPUT"))
val outputPath = Option(System.getenv("OUTPUT"))
val refPath = Option(System.getenv("REFERENCE"))

if (!inputPath.isDefined || !outputPath.isDefined || !refPath.isDefined) {
  logger.error("INPUT, OUTPUT, and REFERENCE environment variables are required")
  System.exit(1)
}

System.getProperties().put("samjdk.reference_fasta", refPath.get)

val alignments = sc.loadBam(inputPath.get)
alignments.saveAsSam(outputPath.get, asSingleFile = true)

System.exit(0)
