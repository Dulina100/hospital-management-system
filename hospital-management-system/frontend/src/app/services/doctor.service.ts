import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {
  private apiUrl = 'http://localhost:8080/api/doctors';

  constructor(private http: HttpClient) { }

  // Get all doctors
  getAllDoctors(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  // Get doctor by ID
  getDoctorById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  // Create doctor
  createDoctor(doctor: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, doctor);
  }

  // Update doctor
  updateDoctor(id: number, doctor: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, doctor);
  }

  // Delete doctor
  deleteDoctor(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

  // Get doctors by specialization
  getDoctorsBySpecialization(specialization: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/specialization/${specialization}`);
  }

  // Get doctors by department
  getDoctorsByDepartment(department: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/department/${department}`);
  }

  // Get available doctors
  getAvailableDoctors(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/available`);
  }
}
