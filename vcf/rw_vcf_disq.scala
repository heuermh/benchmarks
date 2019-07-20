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
import org.disq_bio.disq.HtsjdkVariantsRdd
import org.disq_bio.disq.HtsjdkVariantsRddStorage
import org.disq_bio.disq.FileCardinalityWriteOption
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger("rw_vcf_disq")
val inputPath = Option(System.getenv("INPUT"))
val outputPath = Option(System.getenv("OUTPUT"))

if (!inputPath.isDefined || !outputPath.isDefined) {
  logger.error("INPUT and OUTPUT environment variables are required")
  System.exit(1)
}

val htsjdkVariantsRddStorage = HtsjdkVariantsRddStorage.makeDefault(sc)
val htsjdkVariantsRdd = htsjdkVariantsRddStorage.read(inputPath.get)
htsjdkVariantsRddStorage.write(htsjdkVariantsRdd, outputPath.get, FileCardinalityWriteOption.SINGLE)

System.exit(0)
