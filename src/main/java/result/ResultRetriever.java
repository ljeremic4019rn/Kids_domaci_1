package result;

import app.App;
import job.ScanType;
import result.results.DirScanResult;
import result.results.Result;

import java.util.Map;

public class ResultRetriever extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                Result result = App.resultQueue.take();

                //todo if poison

                if(result.getScanType() == ScanType.FILE){
                    System.err.println(((DirScanResult)result).getCorpusName() + " dodat u FS results");
                    DirScanResult dirScanResult = (DirScanResult) result;
                    App.corpusScannerResults.put(dirScanResult.getCorpusName(), dirScanResult);
                }
                else if (result.getScanType() == ScanType.WEB){
                    System.err.println(((DirScanResult)result).getCorpusName() + " dodat u WS results");
                    //todo sortiraj web result pravilno
//                    WebScanResult webScanResult = (WebScanResult) result;
//                    fileScannerResults.put(webScanResult.getCorpusName(), webScanResult);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void getResult(String corpusDirName){
        Map<String, Integer> scannerResult = null;
        DirScanResult dirScanResult = App.corpusScannerResults.get(corpusDirName);
        if (dirScanResult != null)
            scannerResult = dirScanResult.getResult();

        if (scannerResult == null) System.err.println("Error loading results from directory " + corpusDirName);

        System.out.println(scannerResult);//todo mozda da se promeni nacin printanja

    }

    public void getSummary(){

    }

}
