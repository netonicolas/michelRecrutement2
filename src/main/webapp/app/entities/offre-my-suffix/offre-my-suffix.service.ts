import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { OffreMySuffix } from './offre-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class OffreMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/offres';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/offres';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(offre: OffreMySuffix): Observable<OffreMySuffix> {
        const copy = this.convert(offre);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(offre: OffreMySuffix): Observable<OffreMySuffix> {
        const copy = this.convert(offre);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<OffreMySuffix> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to OffreMySuffix.
     */
    private convertItemFromServer(json: any): OffreMySuffix {
        const entity: OffreMySuffix = Object.assign(new OffreMySuffix(), json);
        entity.dateOffres = this.dateUtils
            .convertLocalDateFromServer(json.dateOffres);
        return entity;
    }

    /**
     * Convert a OffreMySuffix to a JSON which can be sent to the server.
     */
    private convert(offre: OffreMySuffix): OffreMySuffix {
        const copy: OffreMySuffix = Object.assign({}, offre);
        copy.dateOffres = this.dateUtils
            .convertLocalDateToServer(offre.dateOffres);
        return copy;
    }
}
