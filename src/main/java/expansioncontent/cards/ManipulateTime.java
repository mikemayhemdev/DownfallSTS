package expansioncontent.cards;


import com.megacrit.cardcrawl.actions.unique.ForethoughtAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.actions.ManipulateTimeAction;
import expansioncontent.expansionContentMod;

import static expansioncontent.expansionContentMod.loadJokeCardImage;

public class ManipulateTime extends AbstractExpansionCard {
    public final static String ID = makeID("ManipulateTime");

    public ManipulateTime() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        //todo skill bg instead of power bg
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_timeeater.png", "expansioncontentResources/images/1024/bg_boss_timeeater.png");
        loadJokeCardImage(this, "ManipulateTime.png");
        tags.add(expansionContentMod.STUDY_TIMEEATER);
        tags.add(expansionContentMod.STUDY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToTop(new ManipulateTimeAction(this.upgraded));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

}

