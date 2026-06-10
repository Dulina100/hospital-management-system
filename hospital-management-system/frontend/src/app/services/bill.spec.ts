import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BillService {
  private apiUrl = 'http://localhost:8080/api/bills';

  constructor(private http: HttpClient) { }

  // Get all bills
  getAllBills(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  // Get bill by ID
  getBillById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  // Create bill
  createBill(bill: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, bill);
  }

  // Update bill
  updateBill(id: number, bill: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, bill);
  }

  // Delete bill
  deleteBill(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

  // Get bills by patient
  getBillsByPatientId(patientId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/patient/${patientId}`);
  }

  // Get bills by status
  getBillsByStatus(status: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/status/${status}`);
  }

  // Process payment
  processPayment(billId: number, amount: number, paymentMethod: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/${billId}/pay?amount=${amount}&paymentMethod=${paymentMethod}`, {});
  }
}
