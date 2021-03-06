package com.bridgelabz;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
    try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {

        Iterator<IndiaCensusCSV> censusCSVIterator = getCSVFileIterator(reader, IndiaCensusCSV.class);
        Iterable<IndiaCensusCSV> csvIterable = () -> censusCSVIterator;
        return (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
    } catch (IOException e) {
        throw new CensusAnalyserException(e.getMessage(),
                CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
    } catch (IllegalStateException e){
        throw new CensusAnalyserException(e.getMessage(),
                CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
    } catch (RuntimeException e){
        throw new CensusAnalyserException(e.getMessage(),
                CensusAnalyserException.ExceptionType.NOT_A_CSV_TYPE);
    }
}

    public int loadIndiaStateCode(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){

            Iterator<IndiaStateCodeCSV> censusCSVIterator = getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> censusCSVIterator;
            return (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    private <E> Iterator<E> getCSVFileIterator (Reader reader,
                                                Class<E> csvClass)throws CensusAnalyserException{
        try{
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return csvToBean.iterator();
        }catch (IllegalStateException e){
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (RuntimeException e){
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.NOT_A_CSV_TYPE);
        }
    }

}
