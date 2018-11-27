import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWydawnictwoMySuffix } from 'app/shared/model/wydawnictwo-my-suffix.model';
import { WydawnictwoMySuffixService } from './wydawnictwo-my-suffix.service';

@Component({
    selector: 'jhi-wydawnictwo-my-suffix-delete-dialog',
    templateUrl: './wydawnictwo-my-suffix-delete-dialog.component.html'
})
export class WydawnictwoMySuffixDeleteDialogComponent {
    wydawnictwo: IWydawnictwoMySuffix;

    constructor(
        private wydawnictwoService: WydawnictwoMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.wydawnictwoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'wydawnictwoListModification',
                content: 'Deleted an wydawnictwo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-wydawnictwo-my-suffix-delete-popup',
    template: ''
})
export class WydawnictwoMySuffixDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ wydawnictwo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(WydawnictwoMySuffixDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.wydawnictwo = wydawnictwo;
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
