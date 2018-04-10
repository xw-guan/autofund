/**
 * 
 */
var stock = {
    constant : {
        itemPerPage : 10
    },
    URL : {
        update : function() {
            return '/autofund/stock/update';
        },
        query : function(symbolOrName) {
            return '/autofund/stock/' + symbolOrName;
        }
    },
    init : function() {
        $('#searchBtn').click(function() {
            var symbolOrName = $('#symbolOrName').val();
            console.log(symbolOrName); //TODO
            if (symbolOrName != null && symbolOrName != "") {
                $.get(stock.URL.query($('#symbolOrName').val()), {}, function(result) {
                    if (result && result['success']) {
                        console.log(result); // TODO
                    }
                });
            }
        });
        $('#listAllBtn').click(function() {
            alert('等待实现');
        });

        $('#updateDetailBtn').click(function() {
            alert('请先执行更新'); // TODO
        });

        $('#updateBtn').click(function() {
            $('#updateProcess').html('&nbsp&nbsp&nbsp&nbsp正在更新, 请稍候...'); // TODO 实时显示更新数量
            $.post(stock.URL.update(), {}, function(result) {
                if (result && result['success']) {
                    console.log(result); // TODO
                    var updateStateList = result['data'];
                    var success = 0;
                    var fail = 0;
                    $.each(updateStateList, function(i, state) {
                        if (state['stockDataSuccess'] >= 0 && state['maSuccess'] >= 0) {
                            success++;
                        } else {
                            fail++;
                        }
                    });
                    if (fail == 0) {
                        $('#updateState').html('已是最新');
                    } else {
                        $('#updateState').html('需要更新');
                    }
                    $('#updateProcess').html('&nbsp&nbsp&nbsp&nbsp更新完成, ' + success + '成功' + fail + '失败');
                    $('#updateDetailBtn').click(function() {
                        alert('等待实现'); // TODO
                    });
                }
            });
        });
    }
}