import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {
  private apiUrl = 'http://localhost:8080/api/appointments';

  constructor(private http: HttpClient) { }

  // Get all appointments
  getAllAppointments(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  // Get appointment by ID
  getAppointmentById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  // Create appointment
  createAppointment(appointment: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, appointment);
  }

  // Update appointment
  updateAppointment(id: number, appointment: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, appointment);
  }

  // Delete appointment
  deleteAppointment(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

  // Get appointments by patient
  getAppointmentsByPatientId(patientId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/patient/${patientId}`);
  }

  // Get appointments by doctor
  getAppointmentsByDoctorId(doctorId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/doctor/${doctorId}`);
  }

  // Get appointments by status
  getAppointmentsByStatus(status: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/status/${status}`);
  }

  // Cancel appointment
  cancelAppointment(id: number): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}/cancel`, {});
  }

  // Complete appointment
  completeAppointment(id: number): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}/complete`, {});
  }

  // Reschedule appointment
  rescheduleAppointment(id: number, newDate: string): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}/reschedule?newDate=${newDate}`, {});
  }
}
