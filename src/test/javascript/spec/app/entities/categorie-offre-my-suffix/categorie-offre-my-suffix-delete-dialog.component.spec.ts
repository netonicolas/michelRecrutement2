/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MichelRecrutement2TestModule } from '../../../test.module';
import { CategorieOffreMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/categorie-offre-my-suffix/categorie-offre-my-suffix-delete-dialog.component';
import { CategorieOffreMySuffixService } from '../../../../../../main/webapp/app/entities/categorie-offre-my-suffix/categorie-offre-my-suffix.service';

describe('Component Tests', () => {

    describe('CategorieOffreMySuffix Management Delete Component', () => {
        let comp: CategorieOffreMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<CategorieOffreMySuffixDeleteDialogComponent>;
        let service: CategorieOffreMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MichelRecrutement2TestModule],
                declarations: [CategorieOffreMySuffixDeleteDialogComponent],
                providers: [
                    CategorieOffreMySuffixService
                ]
            })
            .overrideTemplate(CategorieOffreMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CategorieOffreMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategorieOffreMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
