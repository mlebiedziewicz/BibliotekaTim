import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IKsiazkaMySuffix } from 'app/shared/model/ksiazka-my-suffix.model';
import { KsiazkaMySuffixService } from './ksiazka-my-suffix.service';

@Component({
    selector: 'jhi-ksiazka-my-suffix-delete-dialog',
    templateUrl: './ksiazka-my-suffix-delete-dialog.component.html'
})
export class KsiazkaMySuffixDeleteDialogComponent {
    ksiazka: IKsiazkaMySuffix;

    constructor(
        private ksiazkaService: KsiazkaMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ksiazkaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'ksiazkaListModification',
                content: 'Deleted an ksiazka'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ksiazka-my-suffix-delete-popup',
    template: ''
})
export class KsiazkaMySuffixDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ksiazka }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(KsiazkaMySuffixDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.ksiazka = ksiazka;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
