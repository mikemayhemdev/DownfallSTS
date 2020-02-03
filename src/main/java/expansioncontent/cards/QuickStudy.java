package expansioncontent.cards;



import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.expansionContentMod;

import java.util.*;

public class QuickStudy extends AbstractExpansionCard {
    public final static String ID = makeID("QuickStudy");

    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;

    public QuickStudy() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);


        tags.add(expansionContentMod.STUDY);

        baseMagicNumber = magicNumber = MAGIC;

        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        atb(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, this.magicNumber));

        ArrayList<String> tmp = new ArrayList<>();

        for (Map.Entry<String, AbstractCard> stringAbstractCardEntry : CardLibrary.cards.entrySet()) {
            Map.Entry<String, AbstractCard> c = (Map.Entry) stringAbstractCardEntry;
            if (c.getValue().hasTag(expansionContentMod.STUDY)) {
                tmp.add(c.getKey());
            }
        }


        atb(new MakeTempCardInHandAction(CardLibrary.cards.get(tmp.get(AbstractDungeon.cardRng.random(0, tmp.size() - 1)))));

        /*
        if (upgraded){
            atb(new MakeTempCardInHandAction(CardLibrary.cards.get(tmp.get(AbstractDungeon.cardRng.random(0, tmp.size() - 1)))));

        }
        */

    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
        }
    }

}