/**
 * 
 */
document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("updateCheak");
	
	form.addEventListener("submit", function (e){
		
	const startDateInput = document.getElementById("start_date");
	const endDateInput = document.getElementById("end_date");
	
	if(startDateInputtDateInput.value){
			const formattedStartDate = startDateInput.value.replace(/-/g, "/");
			startDateInput.value = formattedStartDate;
	}
	
	if(endDateInput.value){
		const formattedEndDate = endDateInput.value.replace(/-/g, "/");
		endDateInput.value = formattedEndDate;
	}
	
});	
});