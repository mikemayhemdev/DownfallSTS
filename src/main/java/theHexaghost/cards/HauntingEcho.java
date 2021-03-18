package theHexaghost.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.ExhaustToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import sneckomod.SneckoMod;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.ChargeCurrentFlameAction;
import theHexaghost.actions.ExtinguishCurrentFlameAction;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class HauntingEcho extends AbstractHexaCard {

    public final static String ID = makeID("HauntingEcho");

    //stupid intellij stuff ATTACK, SELF_AND_ENEMY, UNCOMMON

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;

    public HauntingEcho() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.SELF_AND_ENEMY);
        baseDamage = DAMAGE;
        tags.add(HexaMod.GHOSTWHEELCARD);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (!AbstractDungeon.player.exhaustPile.isEmpty()) {
                    ArrayList<AbstractCard> eligible = AbstractDungeon.player.exhaustPile.group.stream().filter(c -> c.hasTag(HexaMod.AFTERLIFE)).collect(Collectors.toCollection(ArrayList::new));  // Very proud of this line
                    if (!eligible.isEmpty()) {
                        AbstractCard q = eligible.get(AbstractDungeon.cardRandomRng.random(eligible.size()-1));
                        att(new AbstractGameAction() {
                            @Override
                            public void update() {
                                isDone = true;
                                AbstractDungeon.player.exhaustPile.removeCard(q);
                                AbstractDungeon.effectsQueue.add(new ShowCardAndAddToDiscardEffect(q.makeSameInstanceOf()));
                            }
                        });
                    }
                }
            }
        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}