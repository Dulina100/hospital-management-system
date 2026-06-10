import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface PatientRequest {
  user: {
    username: string;
    email: string;
    password: string;
    firstName: string;
    lastName: string;
    phone: string;
    role: { id: number };
  };
  dateOfBirth: string;
  gender: string;
  address: string;
  city: string;
  state: string;
  postalCode: string;
  bloodGroup: string;
  medicalHistory: string;
  allergies: string;
  emergencyContactName: string;
  emergencyContactPhone: string;
}

@Injectable({
  providedIn: 'root'
})
export class PatientService {
  private apiUrl = 'http://localhost:8080/api/patients';

  constructor(private http: HttpClient) { }

  // Get all patients
  getAllPatients(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  // Get patient by ID
  getPatientById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  // Create patient
  createPatient(patient: PatientRequest): Observable<any> {
    return this.http.post<any>(this.apiUrl, patient);
  }

  // Update patient
  updatePatient(id: number, patient: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, patient);
  }

  // Delete patient
  deletePatient(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

  // Get admitted patients
  getAdmittedPatients(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/admitted/true`);
  }

  // Get discharged patients
  getDischargedPatients(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/admitted/false`);
  }

  // Search patient
  searchPatient(firstName: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/search?firstName=${firstName}`);
  }
}
