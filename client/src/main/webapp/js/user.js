/**
 * Sidebar menu switch active selected item
 */
$(document).ready(function() {

    let urlParam = new URLSearchParams(window.location.search);

    if(urlParam.has("tab")){

        let param = urlParam.get("tab");
        $("#" + param).addClass("active");
    }
    else{
        $("#info").addClass("active");
    }
});