<script type="text/javascript">
	var ng_config = {
		assests_dir: '${domain.httpLocal?if_exists}/js/ng_calendar_all/assets/'	// the path to the assets directory
	}
</script>
<script type="text/javascript" src="${domain.httpLocal?if_exists}/js/ng_calendar_all/ng_all.js"></script>
<script type="text/javascript" src="${domain.httpLocal?if_exists}/js/ng_calendar_all/components/calendar.js"></script>
<script type="text/javascript">
var dailyStart;
ng.ready(function(){
		// creating the calendar
		dailyStart = new ng.Calendar({
			input: 'dailyStart',	 // the input field id
			start_date: 'year - 1',	 // the start date (default is today)
			display_date: new Date() // the display date (default is start_date)
		});
	});
var dailyEnd;
ng.ready(function(){
		// creating the calendar
		dailyEnd = new ng.Calendar({
			input: 'dailyEnd',	 // the input field id
			start_date: 'year - 1',	 // the start date (default is today)
			display_date: new Date() // the display date (default is start_date)
		});
	});
</script>
