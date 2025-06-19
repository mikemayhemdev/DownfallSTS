package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RitualPower;

import static awakenedOne.AwakenedOneMod.*;

public class CursedBlessing extends CustomRelic {

    public static final String ID = AwakenedOneMod.makeID("CursedBlessing");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("CursedBlessing.png")); //TODO: Images
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("CursedBlessing.png"));


    //required triggers
    private static final int AMOUNT1 = 3;
    //strength gain
    private static final int AMOUNT2 = 1;

    public CursedBlessing() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
     //   DuvuStrings = CardCrawlGame.languagePack.getRelicStrings(DuVuDoll.ID);
        this.counter = -1;
    }

    //Final Rites


//    public void onEquip() {
//        this.counter = 0;
//    }

    //optional anti stalling tech

    public void onEquip() {
        this.counter = -1;
    }

     public void onVictory() {
         this.counter = -1;
     }

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + AMOUNT1 + DESCRIPTIONS[1] + AMOUNT2 + DESCRIPTIONS[2];
    }

    public void onTrigger() {
        if (this.counter != -1) {
        this.counter++;
        if (this.counter == AMOUNT2) {
            this.counter = -1;
            flash();
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            //this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, AMOUNT2), AMOUNT2));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RitualPower(AbstractDungeon.player, AMOUNT2, true), AMOUNT2));
            }
        }
    }


   // public boolean canSpawn() {
    //        Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();
    //
    //        AbstractCard c;
    //        do {
    //            if (!var1.hasNext()) {
    //                return false;
    //            }
    //
    //            c = (AbstractCard)var1.next();
    //        } while(c.type != AbstractCard.CardType.CURSE);
    //
    //        return true;
    //    }
    //
    //
    //    public void atBattleStart() {
    //        this.flash();
    //
    //        Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();
    //
    //        while(var1.hasNext()) {
    //            AbstractCard c = (AbstractCard)var1.next();
    //            if (c.type == AbstractCard.CardType.CURSE) {
    //
    //                Iterator var2 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
    //
    //                while(var2.hasNext()) {
    //                    AbstractMonster mo = (AbstractMonster)var2.next();
    //                    this.addToTop(new RelicAboveCreatureAction(mo, this));
    //                    this.addToTop(new ApplyPowerAction(mo, AbstractDungeon.player, new ManaburnPower(mo, AMOUNT), AMOUNT, true));
    //                }
    //
    //            }
    //        }
    //    }
    //
    //
    //    public void setCounter(int c) {
    //        this.counter = c;
    //        if (this.counter == 0) {
    //            this.description = this.DESCRIPTIONS[0] + AMOUNT + this.DESCRIPTIONS[1] + this.DESCRIPTIONS[2];
    //        } else {
    //            this.description = this.DESCRIPTIONS[0] + AMOUNT + this.DESCRIPTIONS[1] + this.DESCRIPTIONS[3] + this.counter + this.DESCRIPTIONS[4];
    //        }
    //
    //        this.tips.clear();
    //        this.tips.add(new PowerTip(this.name, this.description));
    //        this.initializeTips();
    //    }
    //
    //    public void onMasterDeckChange() {
    //        this.counter = 0;
    //        Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();
    //
    //        while(var1.hasNext()) {
    //            AbstractCard c = (AbstractCard)var1.next();
    //            if (c.type == AbstractCard.CardType.CURSE) {
    //                ++this.counter;
    //            }
    //        }
    //
    //        if (this.counter == 0) {
    //            this.description = this.DESCRIPTIONS[0] + AMOUNT + this.DESCRIPTIONS[1] + this.DESCRIPTIONS[2];
    //        } else {
    //            this.description = this.DESCRIPTIONS[0] + AMOUNT + this.DESCRIPTIONS[1] + this.DESCRIPTIONS[3] + this.counter + this.DESCRIPTIONS[4];
    //        }
    //
    //        this.tips.clear();
    //        this.tips.add(new PowerTip(this.name, this.description));
    //        this.initializeTips();
    //    }
    //
    //    public void onEquip() {
    //        this.counter = 0;
    //        Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();
    //
    //        while(var1.hasNext()) {
    //            AbstractCard c = (AbstractCard)var1.next();
    //            if (c.type == AbstractCard.CardType.CURSE) {
    //                ++this.counter;
    //            }
    //        }
    //
    //        if (this.counter == 0) {
    //            this.description = this.DESCRIPTIONS[0] + AMOUNT + this.DESCRIPTIONS[1] + this.DESCRIPTIONS[2];
    //        } else {
    //            this.description = this.DESCRIPTIONS[0] + AMOUNT + this.DESCRIPTIONS[1] + this.DESCRIPTIONS[3] + this.counter + this.DESCRIPTIONS[4];
    //        }
    //
    //        this.tips.clear();
    //        this.tips.add(new PowerTip(this.name, this.description));
    //        this.initializeTips();
    //    }

    //public void onExhaust(AbstractCard card) {
    //        if (card.type == AbstractCard.CardType.CURSE) {
    //            this.flash();
    //            Iterator var2 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
    //
    //            while (var2.hasNext()) {
    //                AbstractMonster mo = (AbstractMonster) var2.next();
    //                if (!mo.isDead) {
    //                    this.addToTop(new RelicAboveCreatureAction(mo, this));
    //                    this.addToTop(new ApplyPowerAction(mo, AbstractDungeon.player, new ManaburnPower(mo, AMOUNT), AMOUNT, true));
    //                }
    //            }
    //
    //
    //            atb(new AbstractGameAction() {
    //                @Override
    //                public void update() {
    //                    isDone = true;
    //
    //                    for (AbstractPower p : AbstractDungeon.player.powers) {
    //                        if (p instanceof OnLoseEnergyPower) {
    //                            ((OnLoseEnergyPower) p).LoseEnergyAction(1);
    //                        }
    //                    }
    //
    //                    for (AbstractMonster m2 : AbstractDungeon.getMonsters().monsters) {
    //                        if (!m2.isDead && !m2.isDying) {
    //                            for (AbstractPower p : m2.powers) {
    //                                if (p instanceof OnLoseEnergyPower) {
    //                                    ((OnLoseEnergyPower) p).LoseEnergyAction(1);
    //                                }
    //                            }
    //                        }
    //
    //                        for (AbstractRelic p : AbstractDungeon.player.relics) {
    //                            if (p instanceof OnLoseEnergyRelic) {
    //                                ((OnLoseEnergyRelic) p).LoseEnergyAction(1);
    //                            }
    //                        }
    //                    }
    //                }
    //            });
    //        }
    //    }
}