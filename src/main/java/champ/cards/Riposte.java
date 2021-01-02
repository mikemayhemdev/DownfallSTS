package champ.cards;

import champ.ChampMod;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class Riposte extends AbstractChampCard {

    public final static String ID = makeID("Riposte");

    //stupid intellij stuff skill, self, uncommon

    private static final int MAGIC = 6;
    // private static final int UPG_MAGIC = 3;

    public Riposte() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(ChampMod.OPENER);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        this.tags.add(ChampMod.OPENERDEFENSIVE);
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBODEFENSIVE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        defenseOpen();
        if (upgraded) atb(new ApplyPowerAction(p, p, new CounterPower(magicNumber), magicNumber));
        if (dcombo()) {
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    for (AbstractCard q : p.hand.group) {
                        if (q.type == CardType.ATTACK) {
                            addToTop(new ApplyPowerAction(p, p, new CounterPower(magicNumber), magicNumber));
                            //addToTop(new DiscardSpecificCardAction(q, p.hand));
                        }
                    }
                }
            });
        }
    }


    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}