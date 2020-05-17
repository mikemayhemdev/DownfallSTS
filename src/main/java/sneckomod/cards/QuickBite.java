package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleAction;

public class QuickBite extends AbstractSneckoCard {

    public final static String ID = makeID("QuickBite");

    //stupid intellij stuff ATTACK, ENEMY, COMMON

    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 3;

    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 1;

    public QuickBite() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(SneckoMod.RNG);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY), 0.3F));// 117
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.NONE);
        int x = getRandomNum(magicNumber, 2, this);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size() < x) {
                } else if (p.drawPile.isEmpty() || p.drawPile.size() < x) {
                    att(new DrawCardAction(p, x));
                    for (int i = 0; i < x; i++) {
                        int r = i;
                        att(new AbstractGameAction() {
                            @Override
                            public void update() {
                                isDone = true;
                                AbstractCard q = p.drawPile.getNCardFromTop(r);
                                att(new MuddleAction(q));
                            }
                        });
                    }
                    AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());// 34
                } else {
                    isDone = true;
                    att(new DrawCardAction(p, x));
                    for (int i = 0; i < x; i++) {
                        int r = i;
                        att(new AbstractGameAction() {
                            @Override
                            public void update() {
                                isDone = true;
                                AbstractCard q = p.drawPile.getNCardFromTop(r);
                                att(new MuddleAction(q));
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
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}