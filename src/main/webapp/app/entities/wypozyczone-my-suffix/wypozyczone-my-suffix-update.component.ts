import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IWypozyczoneMySuffix } from 'app/shared/model/wypozyczone-my-suffix.model';
import { WypozyczoneMySuffixService } from './wypozyczone-my-suffix.service';
import { IUser, UserService } from 'app/core';
import { IKsiazkaMySuffix } from 'app/shared/model/ksiazka-my-suffix.model';
import { KsiazkaMySuffixService } from 'app/entities/ksiazka-my-suffix';

@Component({
    selector: 'jhi-wypozyczone-my-suffix-update',
    templateUrl: './wypozyczone-my-suffix-update.component.html'
})
export class WypozyczoneMySuffixUpdateComponent implements OnInit {
    wypozyczone: IWypozyczoneMySuffix;
    isSaving: boolean;

    users: IUser[];

    ksiazkas: IKsiazkaMySuffix[];
    datawypozyczeniaDp: any;
    dataoddaniaDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private wypozyczoneService: WypozyczoneMySuffixService,
        private userService: UserService,
        private ksiazkaService: KsiazkaMySuffixService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ wypozyczone }) => {
            this.wypozyczone = wypozyczone;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.ksiazkaService.query().subscribe(
            (res: HttpResponse<IKsiazkaMySuffix[]>) => {
                this.ksiazkas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.wypozyczone.id !== undefined) {
            this.subscribeToSaveResponse(this.wypozyczoneService.update(this.wypozyczone));
        } else {
            this.subscribeToSaveResponse(this.wypozyczoneService.create(this.wypozyczone));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IWypozyczoneMySuffix>>) {
        result.subscribe((res: HttpResponse<IWypozyczoneMySuffix>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackKsiazkaById(index: number, item: IKsiazkaMySuffix) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
