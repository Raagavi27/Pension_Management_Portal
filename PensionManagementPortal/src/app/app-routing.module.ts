import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { ProcessPensionComponent } from './process-pension/process-pension.component';
import { AuthGaurdService } from './service/auth-gaurd.service';

const routes: Routes = [
  { path: '', component:LoginComponent,canActivate:[AuthGaurdService] },
  { path: 'login', component:LoginComponent},
  { path: 'logout', component:LogoutComponent,canActivate:[AuthGaurdService] },
  { path: 'processpension', component:ProcessPensionComponent,canActivate:[AuthGaurdService] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
