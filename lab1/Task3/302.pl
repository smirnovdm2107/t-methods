use strict;
my $k = 0;
my $count = 0;
foreach (<STDIN>) {
    s/<[^>]*>//g;
    if (!(/^\s*$/)) {
        if ($k > 0 && $count > 0) {
            print "\n";
        }
        s/\s*$//g;
        s/^\s*//g;
        s/\s{2,}/ /g;
        print;
        print "\n";
        $k = 1;
        $count = 0;
    } else {
        $count = $count + 1;
    }
}