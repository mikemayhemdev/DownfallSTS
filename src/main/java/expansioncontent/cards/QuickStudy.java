package expansioncontent.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.SelectCardsCenteredAction;
import expansioncontent.expansionContentMod;

import java.util.ArrayList;
import java.util.Collections;

public class QuickStudy extends AbstractExpansionCard {

    public final static String ID = makeID("QuickStudy");
    public String[] NAMES = CardCrawlGame.languagePack.getCharacterString("downfall:OctoChoiceCards").NAMES;
    public String[] TEXT = CardCrawlGame.languagePack.getCharacterString("downfall:OctoChoiceCards").TEXT;

    //stupid intellij stuff SKILL, SELF, RARE

    public QuickStudy() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_skill.png", "expansioncontentResources/images/1024/bg_boss_skill.png");
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> selectionsList = new ArrayList<>();
        ArrayList<AbstractCard> allStudyCardsList = new ArrayList<>();
        for (AbstractCard q : CardLibrary.getAllCards()) {
            if (q.hasTag(expansionContentMod.STUDY)) {
                if (upgraded) q.upgrade();
                allStudyCardsList.add(q);
            }
        }
        Collections.shuffle(allStudyCardsList);
        for (int i = 0; i < 3; i++) {
            selectionsList.add(allStudyCardsList.get(i));
        }
        atb(new SelectCardsCenteredAction(selectionsList, 1, EXTENDED_DESCRIPTION[0], (cards) -> {
            cards.get(0).setCostForTurn(0);
            addToTop(new MakeTempCardInHandAction(cards.get(0), true));
        }));
    }


    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}