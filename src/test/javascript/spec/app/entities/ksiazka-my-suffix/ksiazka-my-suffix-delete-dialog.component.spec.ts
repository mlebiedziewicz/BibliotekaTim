/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BibliotekaTimTestModule } from '../../../test.module';
import { KsiazkaMySuffixDeleteDialogComponent } from 'app/entities/ksiazka-my-suffix/ksiazka-my-suffix-delete-dialog.component';
import { KsiazkaMySuffixService } from 'app/entities/ksiazka-my-suffix/ksiazka-my-suffix.service';

describe('Component Tests', () => {
    describe('KsiazkaMySuffix Management Delete Component', () => {
        let comp: KsiazkaMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<KsiazkaMySuffixDeleteDialogComponent>;
        let service: KsiazkaMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BibliotekaTimTestModule],
                declarations: [KsiazkaMySuffixDeleteDialogComponent]
            })
                .overrideTemplate(KsiazkaMySuffixDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(KsiazkaMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KsiazkaMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
