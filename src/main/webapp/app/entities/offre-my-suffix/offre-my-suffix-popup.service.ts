import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { OffreMySuffix } from './offre-my-suffix.model';
import { OffreMySuffixService } from './offre-my-suffix.service';

@Injectable()
export class OffreMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private offreService: OffreMySuffixService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.offreService.find(id).subscribe((offre) => {
                    if (offre.dateOffres) {
                        offre.dateOffres = {
                            year: offre.dateOffres.getFullYear(),
                            month: offre.dateOffres.getMonth() + 1,
                            day: offre.dateOffres.getDate()
                        };
                    }
                    this.ngbModalRef = this.offreModalRef(component, offre);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.offreModalRef(component, new OffreMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    offreModalRef(component: Component, offre: OffreMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.offre = offre;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
