#!/bin/bash

#
# Copyright 2019 held jointly by the individual authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

set -x

ADAM_SUBMIT="adam-submit"
ADAM_SHELL="adam-shell --packages org.dishevelled:dsh-bio-sequence:1.2"

SEQUENCES=""
export INPUT="$SEQUENCES.fa.gz"

export OUTPUT="$SEQUENCES.sequences.adam"
time ($ADAM_SUBMIT transformSequences $INPUT $OUTPUT)

export OUTPUT="$SEQUENCES.sequences.2bit.adam"
time ($ADAM_SHELL -i writeTwoBitEncodedSequences.scala)

export OUTPUT="$SEQUENCES.sequences.nib.adam"
time ($ADAM_SHELL -i writeNibEncodedSequences.scala)

export OUTPUT="$SEQUENCES.sequences.bam.adam"
time ($ADAM_SHELL -i writeBamEncodedSequences.scala)

ALIGNMENTS=""
export INPUT="$ALIGNMENTS.bam"

export OUTPUT="$ALIGNMENTS.alignments.adam"
time ($ADAM_SUBMIT transformAlignments $INPUT $OUTPUT &> /dev/null)

export OUTPUT="$ALIGNMENTS.alignments.2bit.adam"
time ($ADAM_SHELL -i writeTwoBitEncodedAlignments.scala &> /dev/null)

export OUTPUT="$ALIGNMENTS.alignments.nib.adam"
time ($ADAM_SHELL -i writeNibEncodedAlignments.scala &> /dev/null)

export OUTPUT="$ALIGNMENTS.alignments.bam.adam"
time ($ADAM_SHELL -i writeBamEncodedAlignments.scala &> /dev/null)
