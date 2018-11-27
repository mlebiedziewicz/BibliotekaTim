import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IWydawnictwoMySuffix } from 'app/shared/model/wydawnictwo-my-suffix.model';
import { WydawnictwoMySuffixService } from './wydawnictwo-my-suffix.service';

@Component({
    selector: 'jhi-wydawnictwo-my-suffix-update',
    templateUrl: './wydawnictwo-my-suffix-update.component.html'
})
export class WydawnictwoMySuffixUpdateComponent implements OnInit {
    wydawnictwo: IWydawnictwoMySuffix;
    isSaving: boolean;

    constructor(private wydawnictwoService: WydawnictwoMySuffixService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ wydawnictwo }) => {
            this.wydawnictwo = wydawnictwo;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.wydawnictwo.id !== undefined) {
            this.subscribeToSaveResponse(this.wydawnictwoService.update(this.wydawnictwo));
        } else {
            this.subscribeToSaveResponse(this.wydawnictwoService.create(this.wydawnictwo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IWydawnictwoMySuffix>>) {
        result.subscribe((res: HttpResponse<IWydawnictwoMySuffix>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
