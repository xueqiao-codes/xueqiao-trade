//
// Autogenerated by Thrift Compiler (0.9.1)
//
// DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//


if (typeof xueqiao === 'undefined') {
  xueqiao = {};
}
if (typeof xueqiao.quotation === 'undefined') {
  xueqiao.quotation = {};
}
xueqiao.quotation.QuotationItem = function(args) {
  this.platform = null;
  this.contractSymbol = null;
  this.openPrice = null;
  this.highPrice = null;
  this.lowPrice = null;
  this.preClosePrice = null;
  this.preSettlementPrice = null;
  this.preOpenInterest = null;
  this.openInterest = null;
  this.volumn = null;
  this.turnover = null;
  this.updateTimestampMs = null;
  this.lastPrice = null;
  this.lastQty = null;
  this.upperLimitPrice = null;
  this.lowerLimitPrice = null;
  this.averagePrice = null;
  this.bidPrice = null;
  this.bidQty = null;
  this.askPrice = null;
  this.askQty = null;
  this.receivedTimestampMs = null;
  this.receivedHostName = null;
  this.receivedProcessId = null;
  this.raceTimestampMs = null;
  this.sledExchangeCode = null;
  this.sledCommodityType = null;
  this.sledCommodityCode = null;
  this.sledContractCode = null;
  if (args) {
    if (args.platform !== undefined) {
      this.platform = args.platform;
    }
    if (args.contractSymbol !== undefined) {
      this.contractSymbol = args.contractSymbol;
    }
    if (args.openPrice !== undefined) {
      this.openPrice = args.openPrice;
    }
    if (args.highPrice !== undefined) {
      this.highPrice = args.highPrice;
    }
    if (args.lowPrice !== undefined) {
      this.lowPrice = args.lowPrice;
    }
    if (args.preClosePrice !== undefined) {
      this.preClosePrice = args.preClosePrice;
    }
    if (args.preSettlementPrice !== undefined) {
      this.preSettlementPrice = args.preSettlementPrice;
    }
    if (args.preOpenInterest !== undefined) {
      this.preOpenInterest = args.preOpenInterest;
    }
    if (args.openInterest !== undefined) {
      this.openInterest = args.openInterest;
    }
    if (args.volumn !== undefined) {
      this.volumn = args.volumn;
    }
    if (args.turnover !== undefined) {
      this.turnover = args.turnover;
    }
    if (args.updateTimestampMs !== undefined) {
      this.updateTimestampMs = args.updateTimestampMs;
    }
    if (args.lastPrice !== undefined) {
      this.lastPrice = args.lastPrice;
    }
    if (args.lastQty !== undefined) {
      this.lastQty = args.lastQty;
    }
    if (args.upperLimitPrice !== undefined) {
      this.upperLimitPrice = args.upperLimitPrice;
    }
    if (args.lowerLimitPrice !== undefined) {
      this.lowerLimitPrice = args.lowerLimitPrice;
    }
    if (args.averagePrice !== undefined) {
      this.averagePrice = args.averagePrice;
    }
    if (args.bidPrice !== undefined) {
      this.bidPrice = args.bidPrice;
    }
    if (args.bidQty !== undefined) {
      this.bidQty = args.bidQty;
    }
    if (args.askPrice !== undefined) {
      this.askPrice = args.askPrice;
    }
    if (args.askQty !== undefined) {
      this.askQty = args.askQty;
    }
    if (args.receivedTimestampMs !== undefined) {
      this.receivedTimestampMs = args.receivedTimestampMs;
    }
    if (args.receivedHostName !== undefined) {
      this.receivedHostName = args.receivedHostName;
    }
    if (args.receivedProcessId !== undefined) {
      this.receivedProcessId = args.receivedProcessId;
    }
    if (args.raceTimestampMs !== undefined) {
      this.raceTimestampMs = args.raceTimestampMs;
    }
    if (args.sledExchangeCode !== undefined) {
      this.sledExchangeCode = args.sledExchangeCode;
    }
    if (args.sledCommodityType !== undefined) {
      this.sledCommodityType = args.sledCommodityType;
    }
    if (args.sledCommodityCode !== undefined) {
      this.sledCommodityCode = args.sledCommodityCode;
    }
    if (args.sledContractCode !== undefined) {
      this.sledContractCode = args.sledContractCode;
    }
  }
};
xueqiao.quotation.QuotationItem.prototype = {};
xueqiao.quotation.QuotationItem.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRING) {
        this.platform = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.STRING) {
        this.contractSymbol = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 3:
      if (ftype == Thrift.Type.DOUBLE) {
        this.openPrice = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 4:
      if (ftype == Thrift.Type.DOUBLE) {
        this.highPrice = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 5:
      if (ftype == Thrift.Type.DOUBLE) {
        this.lowPrice = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 6:
      if (ftype == Thrift.Type.DOUBLE) {
        this.preClosePrice = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 7:
      if (ftype == Thrift.Type.DOUBLE) {
        this.preSettlementPrice = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 8:
      if (ftype == Thrift.Type.I64) {
        this.preOpenInterest = input.readI64().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 9:
      if (ftype == Thrift.Type.I64) {
        this.openInterest = input.readI64().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 10:
      if (ftype == Thrift.Type.I64) {
        this.volumn = input.readI64().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 11:
      if (ftype == Thrift.Type.DOUBLE) {
        this.turnover = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 12:
      if (ftype == Thrift.Type.I64) {
        this.updateTimestampMs = input.readI64().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 13:
      if (ftype == Thrift.Type.DOUBLE) {
        this.lastPrice = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 14:
      if (ftype == Thrift.Type.I64) {
        this.lastQty = input.readI64().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 15:
      if (ftype == Thrift.Type.DOUBLE) {
        this.upperLimitPrice = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 16:
      if (ftype == Thrift.Type.DOUBLE) {
        this.lowerLimitPrice = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 17:
      if (ftype == Thrift.Type.DOUBLE) {
        this.averagePrice = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 21:
      if (ftype == Thrift.Type.LIST) {
        var _size0 = 0;
        var _rtmp34;
        this.bidPrice = [];
        var _etype3 = 0;
        _rtmp34 = input.readListBegin();
        _etype3 = _rtmp34.etype;
        _size0 = _rtmp34.size;
        for (var _i5 = 0; _i5 < _size0; ++_i5)
        {
          var elem6 = null;
          elem6 = input.readDouble().value;
          this.bidPrice.push(elem6);
        }
        input.readListEnd();
      } else {
        input.skip(ftype);
      }
      break;
      case 22:
      if (ftype == Thrift.Type.LIST) {
        var _size7 = 0;
        var _rtmp311;
        this.bidQty = [];
        var _etype10 = 0;
        _rtmp311 = input.readListBegin();
        _etype10 = _rtmp311.etype;
        _size7 = _rtmp311.size;
        for (var _i12 = 0; _i12 < _size7; ++_i12)
        {
          var elem13 = null;
          elem13 = input.readI64().value;
          this.bidQty.push(elem13);
        }
        input.readListEnd();
      } else {
        input.skip(ftype);
      }
      break;
      case 23:
      if (ftype == Thrift.Type.LIST) {
        var _size14 = 0;
        var _rtmp318;
        this.askPrice = [];
        var _etype17 = 0;
        _rtmp318 = input.readListBegin();
        _etype17 = _rtmp318.etype;
        _size14 = _rtmp318.size;
        for (var _i19 = 0; _i19 < _size14; ++_i19)
        {
          var elem20 = null;
          elem20 = input.readDouble().value;
          this.askPrice.push(elem20);
        }
        input.readListEnd();
      } else {
        input.skip(ftype);
      }
      break;
      case 24:
      if (ftype == Thrift.Type.LIST) {
        var _size21 = 0;
        var _rtmp325;
        this.askQty = [];
        var _etype24 = 0;
        _rtmp325 = input.readListBegin();
        _etype24 = _rtmp325.etype;
        _size21 = _rtmp325.size;
        for (var _i26 = 0; _i26 < _size21; ++_i26)
        {
          var elem27 = null;
          elem27 = input.readI64().value;
          this.askQty.push(elem27);
        }
        input.readListEnd();
      } else {
        input.skip(ftype);
      }
      break;
      case 100:
      if (ftype == Thrift.Type.I64) {
        this.receivedTimestampMs = input.readI64().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 101:
      if (ftype == Thrift.Type.STRING) {
        this.receivedHostName = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 102:
      if (ftype == Thrift.Type.I16) {
        this.receivedProcessId = input.readI16().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 120:
      if (ftype == Thrift.Type.I64) {
        this.raceTimestampMs = input.readI64().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 135:
      if (ftype == Thrift.Type.STRING) {
        this.sledExchangeCode = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 136:
      if (ftype == Thrift.Type.I16) {
        this.sledCommodityType = input.readI16().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 137:
      if (ftype == Thrift.Type.STRING) {
        this.sledCommodityCode = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 138:
      if (ftype == Thrift.Type.STRING) {
        this.sledContractCode = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

xueqiao.quotation.QuotationItem.prototype.write = function(output) {
  output.writeStructBegin('QuotationItem');
  if (this.platform !== null && this.platform !== undefined) {
    output.writeFieldBegin('platform', Thrift.Type.STRING, 1);
    output.writeString(this.platform);
    output.writeFieldEnd();
  }
  if (this.contractSymbol !== null && this.contractSymbol !== undefined) {
    output.writeFieldBegin('contractSymbol', Thrift.Type.STRING, 2);
    output.writeString(this.contractSymbol);
    output.writeFieldEnd();
  }
  if (this.openPrice !== null && this.openPrice !== undefined) {
    output.writeFieldBegin('openPrice', Thrift.Type.DOUBLE, 3);
    output.writeDouble(this.openPrice);
    output.writeFieldEnd();
  }
  if (this.highPrice !== null && this.highPrice !== undefined) {
    output.writeFieldBegin('highPrice', Thrift.Type.DOUBLE, 4);
    output.writeDouble(this.highPrice);
    output.writeFieldEnd();
  }
  if (this.lowPrice !== null && this.lowPrice !== undefined) {
    output.writeFieldBegin('lowPrice', Thrift.Type.DOUBLE, 5);
    output.writeDouble(this.lowPrice);
    output.writeFieldEnd();
  }
  if (this.preClosePrice !== null && this.preClosePrice !== undefined) {
    output.writeFieldBegin('preClosePrice', Thrift.Type.DOUBLE, 6);
    output.writeDouble(this.preClosePrice);
    output.writeFieldEnd();
  }
  if (this.preSettlementPrice !== null && this.preSettlementPrice !== undefined) {
    output.writeFieldBegin('preSettlementPrice', Thrift.Type.DOUBLE, 7);
    output.writeDouble(this.preSettlementPrice);
    output.writeFieldEnd();
  }
  if (this.preOpenInterest !== null && this.preOpenInterest !== undefined) {
    output.writeFieldBegin('preOpenInterest', Thrift.Type.I64, 8);
    output.writeI64(this.preOpenInterest);
    output.writeFieldEnd();
  }
  if (this.openInterest !== null && this.openInterest !== undefined) {
    output.writeFieldBegin('openInterest', Thrift.Type.I64, 9);
    output.writeI64(this.openInterest);
    output.writeFieldEnd();
  }
  if (this.volumn !== null && this.volumn !== undefined) {
    output.writeFieldBegin('volumn', Thrift.Type.I64, 10);
    output.writeI64(this.volumn);
    output.writeFieldEnd();
  }
  if (this.turnover !== null && this.turnover !== undefined) {
    output.writeFieldBegin('turnover', Thrift.Type.DOUBLE, 11);
    output.writeDouble(this.turnover);
    output.writeFieldEnd();
  }
  if (this.updateTimestampMs !== null && this.updateTimestampMs !== undefined) {
    output.writeFieldBegin('updateTimestampMs', Thrift.Type.I64, 12);
    output.writeI64(this.updateTimestampMs);
    output.writeFieldEnd();
  }
  if (this.lastPrice !== null && this.lastPrice !== undefined) {
    output.writeFieldBegin('lastPrice', Thrift.Type.DOUBLE, 13);
    output.writeDouble(this.lastPrice);
    output.writeFieldEnd();
  }
  if (this.lastQty !== null && this.lastQty !== undefined) {
    output.writeFieldBegin('lastQty', Thrift.Type.I64, 14);
    output.writeI64(this.lastQty);
    output.writeFieldEnd();
  }
  if (this.upperLimitPrice !== null && this.upperLimitPrice !== undefined) {
    output.writeFieldBegin('upperLimitPrice', Thrift.Type.DOUBLE, 15);
    output.writeDouble(this.upperLimitPrice);
    output.writeFieldEnd();
  }
  if (this.lowerLimitPrice !== null && this.lowerLimitPrice !== undefined) {
    output.writeFieldBegin('lowerLimitPrice', Thrift.Type.DOUBLE, 16);
    output.writeDouble(this.lowerLimitPrice);
    output.writeFieldEnd();
  }
  if (this.averagePrice !== null && this.averagePrice !== undefined) {
    output.writeFieldBegin('averagePrice', Thrift.Type.DOUBLE, 17);
    output.writeDouble(this.averagePrice);
    output.writeFieldEnd();
  }
  if (this.bidPrice !== null && this.bidPrice !== undefined) {
    output.writeFieldBegin('bidPrice', Thrift.Type.LIST, 21);
    output.writeListBegin(Thrift.Type.DOUBLE, this.bidPrice.length);
    for (var iter28 in this.bidPrice)
    {
      if (this.bidPrice.hasOwnProperty(iter28))
      {
        iter28 = this.bidPrice[iter28];
        output.writeDouble(iter28);
      }
    }
    output.writeListEnd();
    output.writeFieldEnd();
  }
  if (this.bidQty !== null && this.bidQty !== undefined) {
    output.writeFieldBegin('bidQty', Thrift.Type.LIST, 22);
    output.writeListBegin(Thrift.Type.I64, this.bidQty.length);
    for (var iter29 in this.bidQty)
    {
      if (this.bidQty.hasOwnProperty(iter29))
      {
        iter29 = this.bidQty[iter29];
        output.writeI64(iter29);
      }
    }
    output.writeListEnd();
    output.writeFieldEnd();
  }
  if (this.askPrice !== null && this.askPrice !== undefined) {
    output.writeFieldBegin('askPrice', Thrift.Type.LIST, 23);
    output.writeListBegin(Thrift.Type.DOUBLE, this.askPrice.length);
    for (var iter30 in this.askPrice)
    {
      if (this.askPrice.hasOwnProperty(iter30))
      {
        iter30 = this.askPrice[iter30];
        output.writeDouble(iter30);
      }
    }
    output.writeListEnd();
    output.writeFieldEnd();
  }
  if (this.askQty !== null && this.askQty !== undefined) {
    output.writeFieldBegin('askQty', Thrift.Type.LIST, 24);
    output.writeListBegin(Thrift.Type.I64, this.askQty.length);
    for (var iter31 in this.askQty)
    {
      if (this.askQty.hasOwnProperty(iter31))
      {
        iter31 = this.askQty[iter31];
        output.writeI64(iter31);
      }
    }
    output.writeListEnd();
    output.writeFieldEnd();
  }
  if (this.receivedTimestampMs !== null && this.receivedTimestampMs !== undefined) {
    output.writeFieldBegin('receivedTimestampMs', Thrift.Type.I64, 100);
    output.writeI64(this.receivedTimestampMs);
    output.writeFieldEnd();
  }
  if (this.receivedHostName !== null && this.receivedHostName !== undefined) {
    output.writeFieldBegin('receivedHostName', Thrift.Type.STRING, 101);
    output.writeString(this.receivedHostName);
    output.writeFieldEnd();
  }
  if (this.receivedProcessId !== null && this.receivedProcessId !== undefined) {
    output.writeFieldBegin('receivedProcessId', Thrift.Type.I16, 102);
    output.writeI16(this.receivedProcessId);
    output.writeFieldEnd();
  }
  if (this.raceTimestampMs !== null && this.raceTimestampMs !== undefined) {
    output.writeFieldBegin('raceTimestampMs', Thrift.Type.I64, 120);
    output.writeI64(this.raceTimestampMs);
    output.writeFieldEnd();
  }
  if (this.sledExchangeCode !== null && this.sledExchangeCode !== undefined) {
    output.writeFieldBegin('sledExchangeCode', Thrift.Type.STRING, 135);
    output.writeString(this.sledExchangeCode);
    output.writeFieldEnd();
  }
  if (this.sledCommodityType !== null && this.sledCommodityType !== undefined) {
    output.writeFieldBegin('sledCommodityType', Thrift.Type.I16, 136);
    output.writeI16(this.sledCommodityType);
    output.writeFieldEnd();
  }
  if (this.sledCommodityCode !== null && this.sledCommodityCode !== undefined) {
    output.writeFieldBegin('sledCommodityCode', Thrift.Type.STRING, 137);
    output.writeString(this.sledCommodityCode);
    output.writeFieldEnd();
  }
  if (this.sledContractCode !== null && this.sledContractCode !== undefined) {
    output.writeFieldBegin('sledContractCode', Thrift.Type.STRING, 138);
    output.writeString(this.sledContractCode);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

xueqiao.quotation.KLineQuotationDetail = function(args) {
  this.kLineOpenPrice = null;
  this.kLineHighPrice = null;
  this.kLineLowPrice = null;
  this.kLineClosePrice = null;
  this.kLineQty = null;
  this.kLineSettlementPrice = null;
  this.kLineOpenInterest = null;
  if (args) {
    if (args.kLineOpenPrice !== undefined) {
      this.kLineOpenPrice = args.kLineOpenPrice;
    }
    if (args.kLineHighPrice !== undefined) {
      this.kLineHighPrice = args.kLineHighPrice;
    }
    if (args.kLineLowPrice !== undefined) {
      this.kLineLowPrice = args.kLineLowPrice;
    }
    if (args.kLineClosePrice !== undefined) {
      this.kLineClosePrice = args.kLineClosePrice;
    }
    if (args.kLineQty !== undefined) {
      this.kLineQty = args.kLineQty;
    }
    if (args.kLineSettlementPrice !== undefined) {
      this.kLineSettlementPrice = args.kLineSettlementPrice;
    }
    if (args.kLineOpenInterest !== undefined) {
      this.kLineOpenInterest = args.kLineOpenInterest;
    }
  }
};
xueqiao.quotation.KLineQuotationDetail.prototype = {};
xueqiao.quotation.KLineQuotationDetail.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.DOUBLE) {
        this.kLineOpenPrice = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.DOUBLE) {
        this.kLineHighPrice = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 3:
      if (ftype == Thrift.Type.DOUBLE) {
        this.kLineLowPrice = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 4:
      if (ftype == Thrift.Type.DOUBLE) {
        this.kLineClosePrice = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 5:
      if (ftype == Thrift.Type.I64) {
        this.kLineQty = input.readI64().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 6:
      if (ftype == Thrift.Type.DOUBLE) {
        this.kLineSettlementPrice = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 7:
      if (ftype == Thrift.Type.I64) {
        this.kLineOpenInterest = input.readI64().value;
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

xueqiao.quotation.KLineQuotationDetail.prototype.write = function(output) {
  output.writeStructBegin('KLineQuotationDetail');
  if (this.kLineOpenPrice !== null && this.kLineOpenPrice !== undefined) {
    output.writeFieldBegin('kLineOpenPrice', Thrift.Type.DOUBLE, 1);
    output.writeDouble(this.kLineOpenPrice);
    output.writeFieldEnd();
  }
  if (this.kLineHighPrice !== null && this.kLineHighPrice !== undefined) {
    output.writeFieldBegin('kLineHighPrice', Thrift.Type.DOUBLE, 2);
    output.writeDouble(this.kLineHighPrice);
    output.writeFieldEnd();
  }
  if (this.kLineLowPrice !== null && this.kLineLowPrice !== undefined) {
    output.writeFieldBegin('kLineLowPrice', Thrift.Type.DOUBLE, 3);
    output.writeDouble(this.kLineLowPrice);
    output.writeFieldEnd();
  }
  if (this.kLineClosePrice !== null && this.kLineClosePrice !== undefined) {
    output.writeFieldBegin('kLineClosePrice', Thrift.Type.DOUBLE, 4);
    output.writeDouble(this.kLineClosePrice);
    output.writeFieldEnd();
  }
  if (this.kLineQty !== null && this.kLineQty !== undefined) {
    output.writeFieldBegin('kLineQty', Thrift.Type.I64, 5);
    output.writeI64(this.kLineQty);
    output.writeFieldEnd();
  }
  if (this.kLineSettlementPrice !== null && this.kLineSettlementPrice !== undefined) {
    output.writeFieldBegin('kLineSettlementPrice', Thrift.Type.DOUBLE, 6);
    output.writeDouble(this.kLineSettlementPrice);
    output.writeFieldEnd();
  }
  if (this.kLineOpenInterest !== null && this.kLineOpenInterest !== undefined) {
    output.writeFieldBegin('kLineOpenInterest', Thrift.Type.I64, 7);
    output.writeI64(this.kLineOpenInterest);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

xueqiao.quotation.KLineQuotationMinuteItem = function(args) {
  this.platform = null;
  this.contractSymbol = null;
  this.kMinuteStartTimestampS = null;
  this.kMinutePeriod = null;
  this.detail = null;
  if (args) {
    if (args.platform !== undefined) {
      this.platform = args.platform;
    }
    if (args.contractSymbol !== undefined) {
      this.contractSymbol = args.contractSymbol;
    }
    if (args.kMinuteStartTimestampS !== undefined) {
      this.kMinuteStartTimestampS = args.kMinuteStartTimestampS;
    }
    if (args.kMinutePeriod !== undefined) {
      this.kMinutePeriod = args.kMinutePeriod;
    }
    if (args.detail !== undefined) {
      this.detail = args.detail;
    }
  }
};
xueqiao.quotation.KLineQuotationMinuteItem.prototype = {};
xueqiao.quotation.KLineQuotationMinuteItem.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRING) {
        this.platform = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.STRING) {
        this.contractSymbol = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 3:
      if (ftype == Thrift.Type.I64) {
        this.kMinuteStartTimestampS = input.readI64().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 4:
      if (ftype == Thrift.Type.I16) {
        this.kMinutePeriod = input.readI16().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 5:
      if (ftype == Thrift.Type.STRUCT) {
        this.detail = new xueqiao.quotation.KLineQuotationDetail();
        this.detail.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

xueqiao.quotation.KLineQuotationMinuteItem.prototype.write = function(output) {
  output.writeStructBegin('KLineQuotationMinuteItem');
  if (this.platform !== null && this.platform !== undefined) {
    output.writeFieldBegin('platform', Thrift.Type.STRING, 1);
    output.writeString(this.platform);
    output.writeFieldEnd();
  }
  if (this.contractSymbol !== null && this.contractSymbol !== undefined) {
    output.writeFieldBegin('contractSymbol', Thrift.Type.STRING, 2);
    output.writeString(this.contractSymbol);
    output.writeFieldEnd();
  }
  if (this.kMinuteStartTimestampS !== null && this.kMinuteStartTimestampS !== undefined) {
    output.writeFieldBegin('kMinuteStartTimestampS', Thrift.Type.I64, 3);
    output.writeI64(this.kMinuteStartTimestampS);
    output.writeFieldEnd();
  }
  if (this.kMinutePeriod !== null && this.kMinutePeriod !== undefined) {
    output.writeFieldBegin('kMinutePeriod', Thrift.Type.I16, 4);
    output.writeI16(this.kMinutePeriod);
    output.writeFieldEnd();
  }
  if (this.detail !== null && this.detail !== undefined) {
    output.writeFieldBegin('detail', Thrift.Type.STRUCT, 5);
    this.detail.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

xueqiao.quotation.QuotationPlatform2SledContractEntry = function(args) {
  this.platformContractId = null;
  this.sledPlatform = null;
  this.sledContractId = null;
  if (args) {
    if (args.platformContractId !== undefined) {
      this.platformContractId = args.platformContractId;
    }
    if (args.sledPlatform !== undefined) {
      this.sledPlatform = args.sledPlatform;
    }
    if (args.sledContractId !== undefined) {
      this.sledContractId = args.sledContractId;
    }
  }
};
xueqiao.quotation.QuotationPlatform2SledContractEntry.prototype = {};
xueqiao.quotation.QuotationPlatform2SledContractEntry.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.I64) {
        this.platformContractId = input.readI64().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.STRING) {
        this.sledPlatform = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 3:
      if (ftype == Thrift.Type.I64) {
        this.sledContractId = input.readI64().value;
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

xueqiao.quotation.QuotationPlatform2SledContractEntry.prototype.write = function(output) {
  output.writeStructBegin('QuotationPlatform2SledContractEntry');
  if (this.platformContractId !== null && this.platformContractId !== undefined) {
    output.writeFieldBegin('platformContractId', Thrift.Type.I64, 1);
    output.writeI64(this.platformContractId);
    output.writeFieldEnd();
  }
  if (this.sledPlatform !== null && this.sledPlatform !== undefined) {
    output.writeFieldBegin('sledPlatform', Thrift.Type.STRING, 2);
    output.writeString(this.sledPlatform);
    output.writeFieldEnd();
  }
  if (this.sledContractId !== null && this.sledContractId !== undefined) {
    output.writeFieldBegin('sledContractId', Thrift.Type.I64, 3);
    output.writeI64(this.sledContractId);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

xueqiao.quotation.SledContractMappingEntry = function(args) {
  this.sledContractId = null;
  this.selectedPlatform = null;
  this.selectedPlatformContractId = null;
  this.moneyRatio = null;
  if (args) {
    if (args.sledContractId !== undefined) {
      this.sledContractId = args.sledContractId;
    }
    if (args.selectedPlatform !== undefined) {
      this.selectedPlatform = args.selectedPlatform;
    }
    if (args.selectedPlatformContractId !== undefined) {
      this.selectedPlatformContractId = args.selectedPlatformContractId;
    }
    if (args.moneyRatio !== undefined) {
      this.moneyRatio = args.moneyRatio;
    }
  }
};
xueqiao.quotation.SledContractMappingEntry.prototype = {};
xueqiao.quotation.SledContractMappingEntry.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.I64) {
        this.sledContractId = input.readI64().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.STRING) {
        this.selectedPlatform = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 3:
      if (ftype == Thrift.Type.I64) {
        this.selectedPlatformContractId = input.readI64().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 4:
      if (ftype == Thrift.Type.DOUBLE) {
        this.moneyRatio = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

xueqiao.quotation.SledContractMappingEntry.prototype.write = function(output) {
  output.writeStructBegin('SledContractMappingEntry');
  if (this.sledContractId !== null && this.sledContractId !== undefined) {
    output.writeFieldBegin('sledContractId', Thrift.Type.I64, 1);
    output.writeI64(this.sledContractId);
    output.writeFieldEnd();
  }
  if (this.selectedPlatform !== null && this.selectedPlatform !== undefined) {
    output.writeFieldBegin('selectedPlatform', Thrift.Type.STRING, 2);
    output.writeString(this.selectedPlatform);
    output.writeFieldEnd();
  }
  if (this.selectedPlatformContractId !== null && this.selectedPlatformContractId !== undefined) {
    output.writeFieldBegin('selectedPlatformContractId', Thrift.Type.I64, 3);
    output.writeI64(this.selectedPlatformContractId);
    output.writeFieldEnd();
  }
  if (this.moneyRatio !== null && this.moneyRatio !== undefined) {
    output.writeFieldBegin('moneyRatio', Thrift.Type.DOUBLE, 4);
    output.writeDouble(this.moneyRatio);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

xueqiao.quotation.PLATFORM_CTP = 'CTP';
xueqiao.quotation.PLATFORM_SIMU_CTP = 'SCTP';
xueqiao.quotation.PLATFORM_SP = 'SP';
xueqiao.quotation.PLATFORM_SIMU_SP = 'ESP';
xueqiao.quotation.PLATFORM_ESUNNY = 'ES';
xueqiao.quotation.PLATFROM_SIMY_ESUNNY = 'SES';
xueqiao.quotation.PLATFORM_XUEQIAO = 'XQ';
xueqiao.quotation.PLATFORM_SIMU_XUEQIAO = 'SXQ';