package demo.testvalid.service;

import demo.testvalid.model.BorrowRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService {
    private static List<BorrowRequest> requests = new ArrayList<>();

    public List<BorrowRequest> getAllRequests() {
        return requests;
    }

    public void addRequest(BorrowRequest request) {
        requests.add(request);
    }
}
