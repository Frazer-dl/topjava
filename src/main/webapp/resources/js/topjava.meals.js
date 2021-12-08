let mealAjaxUrl = "ajax/profile/meals/";

// https://stackoverflow.com/a/5064235/548473
let ctx = {
    ajaxUrl: mealAjaxUrl,
    update: function () {
        $.ajax({
            type: "GET",
            url: mealAjaxUrl + "filter?",
            data: $("#filter").serialize()
        }).done(updateTable);
    }
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    )
})

function clearFilterMealsByDate() {
    $("#filter")[0].reset();
    refresh();
}


