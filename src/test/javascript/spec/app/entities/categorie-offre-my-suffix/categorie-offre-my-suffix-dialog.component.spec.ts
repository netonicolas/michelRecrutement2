/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MichelRecrutement2TestModule } from '../../../test.module';
import { CategorieOffreMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/categorie-offre-my-suffix/categorie-offre-my-suffix-dialog.component';
import { CategorieOffreMySuffixService } from '../../../../../../main/webapp/app/entities/categorie-offre-my-suffix/categorie-offre-my-suffix.service';
import { CategorieOffreMySuffix } from '../../../../../../main/webapp/app/entities/categorie-offre-my-suffix/categorie-offre-my-suffix.model';

describe('Component Tests', () => {

    describe('CategorieOffreMySuffix Management Dialog Component', () => {
        let comp: CategorieOffreMySuffixDialogComponent;
        let fixture: ComponentFixture<CategorieOffreMySuffixDialogComponent>;
        let service: CategorieOffreMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MichelRecrutement2TestModule],
                declarations: [CategorieOffreMySuffixDialogComponent],
                providers: [
                    CategorieOffreMySuffixService
                ]
            })
            .overrideTemplate(CategorieOffreMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CategorieOffreMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategorieOffreMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CategorieOffreMySuffix(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.categorieOffre = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'categorieOffreListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CategorieOffreMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.categorieOffre = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'categorieOffreListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
