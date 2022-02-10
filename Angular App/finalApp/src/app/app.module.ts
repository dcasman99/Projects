import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms'
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { StudentListComponent } from './components/student-list/student-list.component';

import { HttpClientModule  } from '@angular/common/http';
import { StudentEditComponent } from './components/student-edit/student-edit.component';
import { ConfirmationComponent } from './components/confirmation/confirmation.component'

@NgModule({
  declarations: [
    AppComponent,
    StudentListComponent,
    StudentEditComponent,
    ConfirmationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
