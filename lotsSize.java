//+------------------------------------------------------------------+
//|                                                      sizeLot.mq4 |
//|                        Copyright 2020, MetaQuotes Software Corp. |
//|                                             https://www.mql5.com |
//+------------------------------------------------------------------+
#property copyright "Copyright 2020, MetaQuotes Software Corp."
#property link      "https://www.mql5.com"
#property version   "1.00"
#property strict
#property indicator_chart_window

double pips;
double spread_value;

input double risk = 1; //risk %


//+------------------------------------------------------------------+
//| Custom indicator initialization function                         |
//+------------------------------------------------------------------+
int OnInit()
  {

   double ticksize = MarketInfo(Symbol(), MODE_TICKSIZE);
   if(ticksize == 0.00001 || ticksize == 0.001)
      pips = ticksize * 10;
   else
      pips = ticksize;

   EventSetTimer(5);

   return(INIT_SUCCEEDED);
  }
//+------------------------------------------------------------------+
//| Custom indicator iteration function                              |
//+------------------------------------------------------------------+
int OnCalculate(const int rates_total,
                const int prev_calculated,
                const datetime &time[],
                const double &open[],
                const double &high[],
                const double &low[],
                const double &close[],
                const long &tick_volume[],
                const long &volume[],
                const int &spread[])
  {
//---

//--- return value of prev_calculated for next call
   return(rates_total);
  }
//+------------------------------------------------------------------+
//| Timer function                                                   |
//+------------------------------------------------------------------+
void OnTimer()
  {
   spread_value = MarketInfo(NULL ,MODE_SPREAD) / 10;
   
   int slArr[13] = { 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29 };
   double sizesArr[13];
   for (int i = 0; i < ArraySize(slArr); i++){
      sizesArr[i] = lotsCalculation(slArr[i]);
   }
   Comment(
   "spread:        " + spread_value + "\n" +
   "lots-5p:   " + sizesArr[12] + "\n" +
   "lots-7p:   " + sizesArr[0] + "\n" +
   "lots-9p:   " + sizesArr[1] + "\n" +
   "lots-11p:   " + sizesArr[2] + "\n" +
   "lots-13p:   " + sizesArr[3] + "\n" +
   "lots-15p:   " + sizesArr[4] + "\n" +
   "lots-17p:   " + sizesArr[5] + "\n" +
   "lots-19p:   " + sizesArr[6] + "\n" +
   "lots-21p:   " + sizesArr[7] + "\n" +
   "lots-23p:   " + sizesArr[8] + "\n" +
   "lots-25p:   " + sizesArr[9] + "\n" +
   "lots-27p:   " + sizesArr[10] + "\n" +
   "lots-29p:   " + sizesArr[11]
   );

  }

//+------------------------------------------------------------------+
//|                                                                  |
//+------------------------------------------------------------------+
double lotsCalculation(int sl)
  {
   double valueToRisk   = risk / 100 * AccountBalance();
   double riskPerPip = valueToRisk / sl;
   double pipValue = 10 * MarketInfo(Symbol(), MODE_TICKVALUE);
   double lot = riskPerPip / pipValue;
   return NormalizeDouble(lot, 3);
  }

//+------------------------------------------------------------------+

