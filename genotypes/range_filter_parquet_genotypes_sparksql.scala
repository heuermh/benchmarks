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
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger("range_filter_parquet_genotypes_sparksql")
val inputPath = Option(System.getenv("INPUT"))

if (!inputPath.isDefined) {
  logger.error("INPUT environment variable is required")
  System.exit(1)
}

val ranges = Seq(ReferenceRegion.fromGenomicRange("chr1", 100, 200), ReferenceRegion.fromGenomicRange("chr2", 100, 200))

val query = new StringBuilder("select count(*) from genotypes g where ")
query.append(
  ranges
    .map(r => "(g.referenceName = %s and g.end > %d and g.start < %d)".format(r.referenceName, r.start, r.end))
    .reduce((a, b) => a + " or " + b)
)

// use spark sql directly
val df = spark.read.parquet(inputPath.get)
df.createOrReplaceTempView("genotypes")
println(spark.sql(query.toString).first.getLong(0))

System.exit(0)
