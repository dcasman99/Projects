import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ConfirmationComponent } from './components/confirmation/confirmation.component';
import { StudentEditComponent } from './components/student-edit/student-edit.component';
import { StudentListComponent } from './components/student-list/student-list.component';

const routes: Routes = [
  {path: '', component: StudentEditComponent},
  {path: 'list', component: StudentListComponent},
  {path: 'form', component: StudentEditComponent},
  {path: 'confirmation', component: ConfirmationComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
