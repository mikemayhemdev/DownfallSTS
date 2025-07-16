package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleAction;
import sneckomod.actions.MuddleRandomCardAction;

import java.util.ArrayList;

public class QuickBite extends AbstractSneckoCard {

    public final static String ID = makeID("QuickBite");

    //stupid intellij stuff ATTACK, ENEMY, COMMON

    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 1;

    public QuickBite() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = 1;
        SneckoMod.loadJokeCardImage(this, "QuickBite.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY), 0.3F));// 117
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.NONE);
        ArrayList<AbstractCard> preHand = new ArrayList<>(p.hand.group);
        addToBot(new DrawCardAction(magicNumber, new AbstractGameAction() {
            @Override
            public void update() {
                ArrayList<AbstractCard> drawnCards = new ArrayList<>();
                for (AbstractCard card : p.hand.group) {
                    if (!preHand.contains(card)) {
                        drawnCards.add(card);
                    }
                }
                for (AbstractCard card : drawnCards) {
                    addToBot(new MuddleAction(card));
                }
                isDone = true;
            }
        }));

    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            upgradeMagicNumber(1);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}