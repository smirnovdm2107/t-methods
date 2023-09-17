use strict;
my $k = 0;
my $count = 0;
foreach my $line (<STDIN>) {
    if (!($line =~ /^ *$/)) {
        if ($k && $count > 0) {
            print "\n";
        }
        $line =~ s/^( *)(.*)( *)$/\2/;
        $line =~ s/ {2,}/ /g;
        print $line;
        $k = 1;
        $count = 0;
    } else {
        $count = $count + 1;
    }
}