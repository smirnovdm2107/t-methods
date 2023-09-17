while(<>) {
    print if /.*(^|\W)(\w+)\2($|\W).*/
}