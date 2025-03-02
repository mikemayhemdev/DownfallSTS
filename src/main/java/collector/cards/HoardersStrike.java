package collector.cards;

import collector.CollectorCollection;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;
import static collector.util.Wiz.att;

public class HoardersStrike extends AbstractCollectorCard {
    public final static String ID = makeID(HoardersStrike.class.getSimpleName());
    // intellij stuff attack, enemy, rare, 12, 5, , , , 

    public HoardersStrike() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 16;
        tags.add(CardTags.STRIKE);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (!CollectorCollection.combatCollection.isEmpty()) {
                    AbstractCard toPlay = CollectorCollection.combatCollection.getTopCard();
                    CollectorCollection.combatCollection.removeCard(toPlay);
                    AbstractDungeon.player.drawPile.addToTop(toPlay);
                    att(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false));
                }
            }
        });
    }

    public void upp() {
        upgradeDamage(4);
    }
}