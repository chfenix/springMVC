var DISTRICTS;
var provinceCode = '',
cityCode = '',
districtCode = '';
//筛选的地区
var province_selector = $('#province_selector'),
city_selector = $('#city_selector'),
district_selector = $('#district_selector');
var htm = '';

$(function() {
	
	    $.ajax({
	        type: 'GET',
	        url: '/cfms/resources/plugins/jsUtils/districts.json',
	        dataType: 'json'
	    }).done(function (res) {
	        DISTRICTS = res;
	        htm = '<option value="">请选择省份</option>';
	        for(var key in DISTRICTS['100000']) {
	        	if(key==$("#provinceCode").val()){
	            	htm +='<option value="'+key+'" selected="selected">'+DISTRICTS['100000'][key]+'</option>';
	            }else{
	            htm += '<option value="'+key+'">'+DISTRICTS['100000'][key]+'</option>';
	            }
	        }
	        province_selector.html(htm);
	        city_selector.html('<option value="">请选择城市</option>');
	        district_selector.html('<option value="">请选择区县</option>');
	        
	        //生成城市的默认选择
	        
	        provinceCode =province_selector.find('option:selected').val();
			
			htm = '<option value="">请选择城市</option>';
			for(var key in DISTRICTS[provinceCode]) {
				if(key==$("#cityCode").val()){
					htm += '<option value="'+key+'" selected="selected">'+DISTRICTS[provinceCode][key]+'</option>';
				}else{
					htm += '<option value="'+key+'">'+DISTRICTS[provinceCode][key]+'</option>';
				}
			}
			city_selector.html(htm);
			district_selector.html('<option value="">请选择区县</option>');
			
			//生成区县的默认选择
			
			cityCode =city_selector.find('option:selected').val();
			
			htm = '<option value="">请选择区县</option>';
			for(var key in DISTRICTS[cityCode]) {
				if(DISTRICTS[cityCode][key]!="市辖区"){
					if(key==$("#districtCode").val()){
						htm += '<option value="'+key+'" selected="selected">'+DISTRICTS[cityCode][key]+'</option>';
					}else{
						htm += '<option value="'+key+'">'+DISTRICTS[cityCode][key]+'</option>';
					}
				}
			}
			district_selector.html(htm);
	        
	    }).fail(function () {
	        console.log('获取地区json数据失败');
	    });
	
	//省生成市下拉框
	$("#province_selector").change(function(){
		provinceCode =province_selector.find('option:selected').val();
		
		console.log(DISTRICTS['100000'][provinceCode]);
		
		$("#provinceName").val(DISTRICTS['100000'][provinceCode]);
		$("#provinceCode").val(provinceCode);
		$("#cityName").val("");
		$("#cityCode").val("");
		$("#districtName").val("");
		$("#districtCode").val("");
		
		htm = '<option value="">请选择城市</option>';
		for(var key in DISTRICTS[provinceCode]) {
			htm += '<option value="'+key+'">'+DISTRICTS[provinceCode][key]+'</option>';
		}
		city_selector.html(htm);
		district_selector.html('<option value="">请选择区县</option>');
	});
	
	//市生成区县下拉框
	$("#city_selector").change(function () {
		cityCode =city_selector.find('option:selected').val();
		
		console.log(DISTRICTS[provinceCode][cityCode]);
		$("#cityName").val(DISTRICTS[provinceCode][cityCode]);
		$("#cityCode").val(cityCode);
		$("#districtName").val("");
		$("#districtCode").val("");
		
		htm = '<option value="">请选择区县</option>';
		
		for(var key in DISTRICTS[cityCode]) {
			if(DISTRICTS[cityCode][key]!="市辖区"){
				htm += '<option value="'+key+'">'+DISTRICTS[cityCode][key]+'</option>';
			}
		}
		district_selector.html(htm);
	});
	
	//区县被选择
	$("#district_selector").change(function () {
		districtCode =district_selector.find('option:selected').val();
		
		console.log(DISTRICTS[cityCode][districtCode]);
		$("#districtName").val(DISTRICTS[cityCode][districtCode]);
		$("#districtCode").val(districtCode);
	});
})

