function pageMove(pageCount, currentPage){
    if (currentPage <= 0 ||currentPage > pageCount ) {
        return;
    }
    location.href = "/board?a=list&&page=" + currentPage;
}
