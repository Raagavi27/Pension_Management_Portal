import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProcessPensionServices } from '../service/services.component';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-process-pension',
  templateUrl: './process-pension.component.html',
  styleUrls: ['./process-pension.component.css']
})
export class ProcessPensionComponent implements OnInit {
 
  pd = {
    bankServiceCharge:'',
    id:'',
    pensionAmount:'',
    aadhaarNumber:''
  }
  error='';
  isShown: boolean = false ;
  aadhaarNumber:string='';
  name:string='';
  dateOfBirth:string='';
  pan:string='';
  typeOfPension:string='';

  bankDetail ={
    id :'',
    bankName:'',
    accountNumber:'',
    typeOfBank:''
  }

  submitted = false;
  
  constructor(private processPensionService: ProcessPensionServices,
    private router: Router) { }
  ngOnInit(): void {}
    
  back():void{
      this.aadhaarNumber='';
      this.submitted=false;
      this.isShown=false;
      this.error='';
    }

  getAadhaarNumber(): void {
    const data = {
      aadhaarNumber: this.aadhaarNumber,
      name:this.name,
      dateOfBirth:this.dateOfBirth,
      pan:this.pan,
      salaryEarned:'',
      allowances:'',
      typeOfPension:this.typeOfPension,
      bankDetail:this.bankDetail
    };

  this.processPensionService.create(data)
    .subscribe(
      response => {
        console.log(response);
        this.pd=response;
        console.log('Successs');
        
      },
      error => {
        this.error="Invalid Aadhaar Number";
        console.log('Failure');
        console.log(error);
        this.submitted=false;
        this.isShown=false;
      },
      ()=>{
        if(this.pd!=null){
        this.isShown = ! this.isShown;
        this.submitted = true;
        } else{
          this.error="Invalid Aadhaar Number";
        }
      }
      
      );
    
    }

};
  


    
  
   

