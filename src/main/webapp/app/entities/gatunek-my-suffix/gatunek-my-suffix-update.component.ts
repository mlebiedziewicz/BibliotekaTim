import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IGatunekMySuffix } from 'app/shared/model/gatunek-my-suffix.model';
import { GatunekMySuffixService } from './gatunek-my-suffix.service';

@Component({
    selector: 'jhi-gatunek-my-suffix-update',
    templateUrl: './gatunek-my-suffix-update.component.html'
})
export class GatunekMySuffixUpdateComponent implements OnInit {
    gatunek: IGatunekMySuffix;
    isSaving: boolean;

    constructor(private gatunekService: GatunekMySuffixService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ gatunek }) => {
            this.gatunek = gatunek;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.gatunek.id !== undefined) {
            this.subscribeToSaveResponse(this.gatunekService.update(this.gatunek));
        } else {
            this.subscribeToSaveResponse(this.gatunekService.create(this.gatunek));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGatunekMySuffix>>) {
        result.subscribe((res: HttpResponse<IGatunekMySuffix>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
