import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IAutorMySuffix } from 'app/shared/model/autor-my-suffix.model';
import { AutorMySuffixService } from './autor-my-suffix.service';

@Component({
    selector: 'jhi-autor-my-suffix-update',
    templateUrl: './autor-my-suffix-update.component.html'
})
export class AutorMySuffixUpdateComponent implements OnInit {
    autor: IAutorMySuffix;
    isSaving: boolean;

    constructor(private autorService: AutorMySuffixService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ autor }) => {
            this.autor = autor;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.autor.id !== undefined) {
            this.subscribeToSaveResponse(this.autorService.update(this.autor));
        } else {
            this.subscribeToSaveResponse(this.autorService.create(this.autor));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAutorMySuffix>>) {
        result.subscribe((res: HttpResponse<IAutorMySuffix>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
