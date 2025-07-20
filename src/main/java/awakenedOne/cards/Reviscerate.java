package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;

public class Reviscerate extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Reviscerate.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public Reviscerate() {
        super(ID, 6, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 6;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        loadJokeCardImage(this, makeBetaCardPath(Reviscerate.class.getSimpleName() + ".png"));

        if (CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null) {
            this.configureCostsOnNewCard();
        }
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++) {
            dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        }
    }

    public void configureCostsOnNewCard() {
        Iterator var1 = AbstractDungeon.actionManager.cardsPlayedThisCombat.iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            if (c.type == CardType.POWER) {
                this.updateCost(-1);
            }
        }

    }

    public void triggerOnCardPlayed(AbstractCard c) {
        if (c.type == CardType.POWER) {
            this.updateCost(-1);
        }

    }

    @Override
    public void upp() {
        //upgradeBaseCost(4);
        upgradeDamage(2);
    }
}