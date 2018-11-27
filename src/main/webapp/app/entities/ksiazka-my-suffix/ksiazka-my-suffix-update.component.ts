import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IKsiazkaMySuffix } from 'app/shared/model/ksiazka-my-suffix.model';
import { KsiazkaMySuffixService } from './ksiazka-my-suffix.service';
import { IAutorMySuffix } from 'app/shared/model/autor-my-suffix.model';
import { AutorMySuffixService } from 'app/entities/autor-my-suffix';
import { IWydawnictwoMySuffix } from 'app/shared/model/wydawnictwo-my-suffix.model';
import { WydawnictwoMySuffixService } from 'app/entities/wydawnictwo-my-suffix';
import { IGatunekMySuffix } from 'app/shared/model/gatunek-my-suffix.model';
import { GatunekMySuffixService } from 'app/entities/gatunek-my-suffix';

@Component({
    selector: 'jhi-ksiazka-my-suffix-update',
    templateUrl: './ksiazka-my-suffix-update.component.html'
})
export class KsiazkaMySuffixUpdateComponent implements OnInit {
    ksiazka: IKsiazkaMySuffix;
    isSaving: boolean;

    autors: IAutorMySuffix[];

    wydawnictwos: IWydawnictwoMySuffix[];

    gatuneks: IGatunekMySuffix[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private ksiazkaService: KsiazkaMySuffixService,
        private autorService: AutorMySuffixService,
        private wydawnictwoService: WydawnictwoMySuffixService,
        private gatunekService: GatunekMySuffixService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ksiazka }) => {
            this.ksiazka = ksiazka;
        });
        this.autorService.query().subscribe(
            (res: HttpResponse<IAutorMySuffix[]>) => {
                this.autors = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.wydawnictwoService.query().subscribe(
            (res: HttpResponse<IWydawnictwoMySuffix[]>) => {
                this.wydawnictwos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.gatunekService.query().subscribe(
            (res: HttpResponse<IGatunekMySuffix[]>) => {
                this.gatuneks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.ksiazka.id !== undefined) {
            this.subscribeToSaveResponse(this.ksiazkaService.update(this.ksiazka));
        } else {
            this.subscribeToSaveResponse(this.ksiazkaService.create(this.ksiazka));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IKsiazkaMySuffix>>) {
        result.subscribe((res: HttpResponse<IKsiazkaMySuffix>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAutorById(index: number, item: IAutorMySuffix) {
        return item.id;
    }

    trackWydawnictwoById(index: number, item: IWydawnictwoMySuffix) {
        return item.id;
    }

    trackGatunekById(index: number, item: IGatunekMySuffix) {
        return item.id;
    }
}
