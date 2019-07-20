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
import htsjdk.variant.variantcontext.VariantContext
import org.disq_bio.disq.HtsjdkVariantsRdd
import org.disq_bio.disq.HtsjdkVariantsRddStorage
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger("filter_bam_disq")
val inputPath = Option(System.getenv("INPUT"))

if (!inputPath.isDefined) {
  logger.error("INPUT environment variable is required")
  System.exit(1)
}

val htsjdkVariantsRddStorage = HtsjdkVariantsRddStorage.makeDefault(sc)
val htsjdkVariantsRdd = htsjdkVariantsRddStorage.read(inputPath.get)
val vcs = htsjdkVariantsRdd.getVariants()

def filter(vc: VariantContext): Boolean = {
  true
}

println(vcs.rdd.filter(filter(_)).count())

System.exit(0)
