# Module Feature Evolution
This module provides the Evolution feature of the Ember application.
It includes functionality for generating models from genetic strings as well
mixing and mutating them.

## Genetics
The artificial genetics used in this system are directly inspired by
human biological genetics, and diverge significantly from most digital
genetics systems which often define their own entirely unique codes and
retain only the permutation aspect of biogenetics. Evolution is often 
described as a slow, iterative process; however, evolution can make
significant changes in single generations due to the accumulation of
"junk" DNA, which for one organism may not be active, but within its
offspring, a single position mutation may suddenly activate brand new
machinery that was not present in any predecessor. These long stretches
of "junk" DNA also provide some resilience against mutagens simply by
existing: they effectively provide sacrificial material for a mutagen
to attack instead of functional material. From here on, we will refer
to these portions of DNA with the respect they deserve for their
contributions by instead referring to them accurately as latent DNA.
We intend to design a system which allows the accumulation of latent
DNA, while also applying a small penalty for longer sequences to
mimic the increasing resource requirements for organisms with larger
genomes.

### Source
Our virtual environment does not need to contend with the entropy of a
biological environment, so we don't find a benefit in storing our codes
as double-stranded, helical DNA structures. Normally DNA would be 
transcribed inta messenger RNA (mRNA), which would then be translated
by ribosomes into proteins. Since we don't need the stability of DNA, we
don't need the intermediate RNA or ribosomes; however, we are interested
in the dynamics of ribosomes, genetically belonging to parents, being
used for the initial transcription of offspring, which includes the
creation of the offsprings dedicated ribosomes and tRNA.

For this illustration, we will treat ribosomes like functions R, which take
a genome G and translation (binding) factors B and return some protein or 
complex of proteins P: `R(G, B) = P`. We will also simplify the process
quite a bit by considering the tRNA a part of the ribosome for the sake
of iterative generation. In reality, the tRNA of one iteration could be
compatible with the same codons of another generation, while carrying
a different amino acid, resulting in cross-contamination that could cause
changes in proteins that would not exist in isolation.

Now consider the case where a parent provides the first ribosomes (R_0). The 
child DNA (G) has just completed recombination, and the child's first organelles
need to be transcribed. Most of these organelles are not of interest to us
directly – our primary concern is the sequence of ribosomes created through
iterations of transcription, which will be initiated by the translation factor (B).
Now let R_c be the child's c-th ribosome where c is any natural number. 
We produce the sequence R_0(G, B) => R_1(G, B) => R_2(G, B) => ... => R_c(G, B).
We are interested to know how often this iterative process converges or
diverges. Since the structure of a ribosome determines the amino acids that
will be attached in response to specific codons, in theory, each ribosome
could have a unique structure that attaches a unique set of amino acids,
resulting in a series of ribosomes, each different from the last. Nature has
obviously devised protection against this existentially chaotic process, so
this is more of a curiosity than a serious concern of this program. As long
as some R_c is found to be equivalent to R_c-1, we can assume that it will
eventually dominate the child's cells and ignore potential products of 
intermediates (even though this might be a good avenue to explore for 
unexplained pathologies).

### The Bases
This system uses 4 bases, which can be labeled in any way convenient to the user.
These 4 bases are simply referred to in the system by their ordinal number 0-3.

### Codons
Each codon is specified by a fixed-length window of 3 bases as it slides along
the genetic sequence. First, an index is calculated for each codon, using the bases as
digits: `sum over p of d*b^p` where p = position of each digit d and b is the
number of bases. For this system, that gives the numbers 0-63, which are then
mapped to artificial amino acids. Once we have a sequence of amino acids, we
replace the biological folding of proteins into complexes and organelles with
an interpretation of the sequence into neural network architecture components.

### Amino Acids and Protein Complexes
Once a ribosome has translated genetics into a protein or set of proteins, the
protein goes through a folding process into its final functional form, often
guided by other proteins or RNA, even while the protein is still being synthesized.
In our system, we only require the Ribosome to produce a list of lists of amino acids,
with each sublist representing a protein sequence. Once each protein sequence is
processed, the proteins are combined into one or more complexes, depending on the
compatibility of each protein with its neighbors.

In our system, we interpret amino acids as arguments and operators using reverse
polish notation. 
